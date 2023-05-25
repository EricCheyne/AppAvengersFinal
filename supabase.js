import { createClient } from "@supabase/supabase-js";


// Create a single supabase client for interacting with your database
export const supabase = createClient('https://pdlpkevmhctaadsbtdfi.supabase.co', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBkbHBrZXZtaGN0YWFkc2J0ZGZpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODIwNDkyNzIsImV4cCI6MTk5NzYyNTI3Mn0.At0WpbWSFkz2pvXXrph3vplkUYoRlJztgeK3x-r0Ccs')
